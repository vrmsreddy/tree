<?php
ini_set('display_errors', 1);
error_reporting(E_ALL);
session_start();

$validUser = 'admin';
$validPass = 'admin';

if (isset($_GET['logout'])) {
    session_destroy();
    header('Location: ' . $_SERVER['PHP_SELF']);
    exit;
}

if (!isset($_SESSION['loggedin'])) {
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        if ($_POST['user'] === $validUser && $_POST['pass'] === $validPass) {
            $_SESSION['loggedin'] = true;
            header('Location: ' . $_SERVER['PHP_SELF']);
            exit;
        } else {
            $error = "Invalid credentials.";
        }
    }
    echo '<!DOCTYPE html><html><head><title>Login</title><meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body { background: #111; color: #eee; font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; }
        form { background: #222; padding: 20px; border-radius: 12px; box-shadow: 0 0 10px #000; }
        input { display: block; margin-bottom: 10px; width: 200px; padding: 8px; border-radius: 6px; border: none; }
        button { padding: 8px 16px; border-radius: 6px; border: none; background: #0cf; color: #000; font-weight: bold; }
    </style></head><body>';
    echo '<form method="post">
        <h2>Login</h2>
        <input name="user" placeholder="Username">
        <input name="pass" type="password" placeholder="Password">
        <button type="submit">Login</button>
        <div style="color:red;">' . ($error ?? '') . '</div>
    </form></body></html>';
    exit;
}

$baseDir = 'media/';
$folders = array_filter(scandir($baseDir), fn($f) => is_dir("$baseDir/$f") && $f[0] !== '.');
$selected = basename($_GET['folder'] ?? $folders[0] ?? '');
$isValid = in_array($selected, $folders);
$mediaBase = $isValid ? "$baseDir/$selected" : '';
$files = $isValid ? array_values(array_filter(scandir($mediaBase), fn($f) => !is_dir("$mediaBase/$f") && preg_match('/\.(jpg|jpeg|png|gif|mp4|webm)$/i', $f))) : [];

if (isset($_GET['ajax'])) {
    header('Content-Type: application/json');
    echo json_encode(array_map(fn($f) => "$mediaBase/$f", $files));
    exit;
}
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Media Viewer</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css">
<style>
:root {
  --bg: #f4f4f4;
  --text: #222;
  --card-bg: #fff;
  --accent: #0cf;
}
body.dark {
  --bg: #121212;
  --text: #eee;
  --card-bg: #1e1e1e;
  --accent: #0af;
}
body {
  margin: 0;
  background: var(--bg);
  color: var(--text);
  font-family: sans-serif;
}
.top-bar {
  background: var(--card-bg);
  padding: 10px 20px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}
.top-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}
select, button {
  padding: 6px 12px;
  border-radius: 6px;
  border: 1px solid #ccc;
  background: var(--card-bg);
  color: var(--text);
}
.grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px,1fr)); gap: 10px; padding: 10px; }
.list { display: flex; flex-direction: column; gap: 15px; padding: 10px; }
.item {
  background: var(--card-bg);
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.item img, .item video {
  width: 100%;
  height: auto;
  object-fit: contain;
}
.item .title {
  text-align: center;
  padding: 8px;
  font-size: 0.9rem;
  color: var(--text);
  word-break: break-all;
}
.swiper {
  width: 100%;
  height: calc(100vh - 100px);
  background: var(--bg);
}
.swiper-slide {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.swiper-slide img, .swiper-slide video {
  max-height: 80vh;
  max-width: 90%;
  object-fit: contain;
}
.swiper-slide .title {
  margin-top: 10px;
  text-align: center;
  font-weight: bold;
  color: var(--text);
}
.swiper-pagination-bullet {
  background: var(--accent);
}
#lightbox {
  display: none;
  position: fixed;
  top:0; left:0;
  width:100%; height:100%;
  background: rgba(0,0,0,0.9);
  justify-content:center;
  align-items:center;
  z-index:1000;
}
#lightbox.active { display:flex; }
#lightbox-content {
  position:relative;
  max-width:90%;
  max-height:90%;
  display:flex;
  justify-content:center;
  align-items:center;
}
#lightbox-content img, #lightbox-content video {
  max-width:100%;
  max-height:90vh;
  object-fit:contain;
}
#closeBtn, #prevBtn, #nextBtn {
  position:absolute;
  top:50%;
  transform:translateY(-50%);
  font-size:2rem;
  color:#fff;
  cursor:pointer;
  padding:10px;
  background:rgba(0,0,0,0.4);
  border-radius:50%;
}
#closeBtn { top:20px; right:20px; transform:none; }
#prevBtn { left:20px; }
#nextBtn { right:20px; }
#loader {
  text-align: center;
  padding: 20px;
  font-size: 1.2em;
}
</style>
</head>
<body>
<div class="top-bar">
  <div class="top-row">
    <div>
      <label for="folderSelect" style="font-weight:bold;">Select Camera:</label>
      <select id="folderSelect">
        <?php foreach ($folders as $f): ?>
          <option value="<?=htmlspecialchars($f)?>" <?=$f===$selected?'selected':''?>><?=$f?></option>
        <?php endforeach; ?>
      </select>
      <button id="gridBtn"><i class="fa fa-th"></i></button>
      <button id="listBtn"><i class="fa fa-list"></i></button>
      <button id="carouselBtn"><i class="fa fa-images"></i></button>
      <button id="playPauseBtn" style="display:none;"><i class="fa fa-pause"></i></button>
    </div>
    <div>
      <button id="themeBtn"><i class="fa fa-moon"></i></button>
      <a href="?logout=1" style="text-decoration:none;"><button><i class="fa fa-sign-out-alt"></i> Logout</button></a>
    </div>
  </div>
</div>

<div id="container" class="grid"></div>
<div id="loader">Loading...</div>

<!-- Lightbox -->
<div id="lightbox">
  <div id="lightbox-content"></div>
  <span id="closeBtn"><i class="fa fa-times"></i></span>
  <span id="prevBtn"><i class="fa fa-chevron-left"></i></span>
  <span id="nextBtn"><i class="fa fa-chevron-right"></i></span>
</div>

<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script>
const folderSelect = document.getElementById('folderSelect');
const container = document.getElementById('container');
const loader = document.getElementById('loader');
const playPauseBtn = document.getElementById('playPauseBtn');
let swiperInstance = null;
let isPlaying = true;
let folder = localStorage.getItem('folder') || folderSelect.value;
let viewMode = localStorage.getItem('viewMode') || 'grid';
let theme = localStorage.getItem('theme') || 'light';
if (theme === 'dark') document.body.classList.add('dark');
folderSelect.value = folder;

document.getElementById('themeBtn').onclick = () => {
  document.body.classList.toggle('dark');
  localStorage.setItem('theme', document.body.classList.contains('dark') ? 'dark' : 'light');
};

function renderItem(f, carousel=false){
  let isVideo = /\.(mp4|webm)$/i.test(f);
  let el = `<div class="item" data-src="${f}" data-type="${isVideo?'video':'image'}">
    ${isVideo ? `<video src="${f}" muted></video>` : `<img src="${f}">`}
    <div class="title">${f.split('/').pop()}</div>
  </div>`;
  return carousel ? `<div class="swiper-slide">${el}</div>` : el;
}

async function loadAll(){
  loader.style.display = 'block';
  const res = await fetch(`?ajax=1&folder=${folder}`);
  const data = await res.json();
  container.innerHTML = data.map(f => renderItem(f)).join('');
  loader.style.display = 'none';
  addLightboxEvents();
  playPauseBtn.style.display = 'none';
}

async function buildCarousel(){
  loader.style.display = 'block';
  const res = await fetch(`?ajax=1&folder=${folder}`);
  const data = await res.json();
  container.innerHTML = `
    <div class="swiper">
      <div class="swiper-wrapper">
        ${data.map(f=>renderItem(f,true)).join('')}
      </div>
      <div class="swiper-pagination"></div>
    </div>`;
  swiperInstance = new Swiper('.swiper',{
    pagination:{el:'.swiper-pagination',clickable:true},
    loop:true,
    autoplay:{delay:3000,disableOnInteraction:false},
  });
  loader.style.display = 'none';
  playPauseBtn.style.display = 'inline-block';
}

playPauseBtn.onclick = ()=>{
  if(!swiperInstance) return;
  if(isPlaying){
    swiperInstance.autoplay.stop();
    playPauseBtn.innerHTML = '<i class="fa fa-play"></i>';
  } else {
    swiperInstance.autoplay.start();
    playPauseBtn.innerHTML = '<i class="fa fa-pause"></i>';
  }
  isPlaying = !isPlaying;
};

function changeView(mode){
  viewMode = mode;
  localStorage.setItem('viewMode', mode);
  container.className = mode;
  if (mode === 'carousel') buildCarousel();
  else loadAll();
}

document.getElementById('gridBtn').onclick = ()=>changeView('grid');
document.getElementById('listBtn').onclick = ()=>changeView('list');
document.getElementById('carouselBtn').onclick = ()=>changeView('carousel');
folderSelect.onchange = ()=>{
  folder = folderSelect.value;
  localStorage.setItem('folder', folder);
  changeView(viewMode);
};

// Lightbox
const lightbox = document.getElementById('lightbox');
const lightboxContent = document.getElementById('lightbox-content');
const closeBtn = document.getElementById('closeBtn');
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
let items = [], currentIndex = 0;

function openLightbox(index){
  currentIndex = index;
  const el = items[index];
  const src = el.dataset.src;
  const type = el.dataset.type;
  lightbox.classList.add('active');
  lightboxContent.innerHTML = type==='video'
    ? `<video src="${src}" controls autoplay></video>`
    : `<img src="${src}">`;
}
function closeLightbox(){ lightbox.classList.remove('active'); lightboxContent.innerHTML=''; }
function nextItem(){ if(currentIndex<items.length-1) openLightbox(currentIndex+1); }
function prevItem(){ if(currentIndex>0) openLightbox(currentIndex-1); }
function addLightboxEvents(){
  items = Array.from(document.querySelectorAll('.item'));
  items.forEach((it,i)=> it.onclick = ()=>openLightbox(i));
}
closeBtn.onclick = closeLightbox;
nextBtn.onclick = nextItem;
prevBtn.onclick = prevItem;
window.onkeydown = e=>{
  if(!lightbox.classList.contains('active')) return;
  if(e.key==='Escape') closeLightbox();
  if(e.key==='ArrowRight') nextItem();
  if(e.key==='ArrowLeft') prevItem();
};

changeView(viewMode);
</script>
</body>
</html>