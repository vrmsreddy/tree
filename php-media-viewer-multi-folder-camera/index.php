<?php
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

$baseDir = 'media';
$folders = array_filter(scandir($baseDir), fn($f) => is_dir("$baseDir/$f") && $f[0] !== '.');
$selected = basename($_GET['folder'] ?? $folders[0] ?? '');
$isValid = in_array($selected, $folders);
$mediaBase = $isValid ? "$baseDir/" . $selected : '';
$files = $isValid ? array_values(array_filter(scandir("$baseDir/$selected"), fn($f) => !is_dir("$baseDir/$selected/$f") && preg_match('/\.(jpg|jpeg|png|gif|mp4|webm)$/i', $f))) : [];

$perPage = 10;
$page = max(1, intval($_GET['page'] ?? 1));
$totalPages = ceil(count($files) / $perPage);
$start = ($page - 1) * $perPage;
$currentFiles = array_slice($files, $start, $perPage);
?><!DOCTYPE html><html>
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
        .layout-buttons, .dark-toggle, .logout {
            display: flex;
            gap: 8px;
        }
        .layout-buttons button, .dark-toggle button, .logout a {
            padding: 6px 12px;
            border: none;
            border-radius: 6px;
            background: var(--accent);
            color: #000;
            cursor: pointer;
            text-decoration: none;
        }
        .media-container {
            padding: 20px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
            gap: 16px;
        }
        .media-container img, .media-container video {
            width: 100%;
            height: auto;
            border-radius: 8px;
            cursor: pointer;
            object-fit: cover;
        }
        .pagination {
            text-align: center;
            margin: 20px;
        }
        .pagination a, .pagination strong {
            margin: 0 5px;
            padding: 4px 10px;
            border-radius: 6px;
            background: var(--accent);
            color: #000;
            text-decoration: none;
        }
        .popup-overlay, .carousel-overlay {
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(0,0,0,0.9);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }
        .popup-overlay img, .swiper-slide img {
            max-width: 90vw;
            max-height: 90vh;
            box-shadow: 0 0 20px #000;
            border-radius: 8px;
        }
        .swiper {
            width: 90vw;
            height: 90vh;
        }
    </style>
</head>
<body class="dark">
<div class="top-bar">
    <div class="top-row">
        <form method="get">
            <select name="folder" onchange="this.form.submit()">
                <option value="" disabled selected>Select Camera</option>
                <?php foreach ($folders as $folder): ?>
                    <option value="<?= htmlspecialchars($folder) ?>" <?= $folder === $selected ? 'selected' : '' ?>><?= htmlspecialchars($folder) ?></option>
                <?php endforeach; ?>
            </select>
        </form><div class="layout-buttons">
        <button onclick="setLayout('grid')"><i class="fas fa-th"></i> Grid</button>
        <button onclick="setLayout('list')"><i class="fas fa-list"></i> List</button>
        <button onclick="openCarousel()"><i class="fas fa-images"></i> Carousel</button>
    </div>

    <div class="dark-toggle">
        <button onclick="toggleDarkMode()">🌓 Dark Mode</button>
    </div>

    <div class="logout">
        <a href="?logout=1">Logout</a>
    </div>
</div>

</div><div class="media-container" id="media">
    <?php foreach ($currentFiles as $file): 
        $safeFile = htmlspecialchars($file);
        $url = "$mediaBase/" . rawurlencode($file);
        if (preg_match('/\.(jpg|jpeg|png|gif)$/i', $file)) {
            echo "<img src=\"$url\" onclick=\"openPopup('$url')\">";
        } else {
            echo "<video src=\"$url\" controls></video>";
        }
    endforeach; ?>
</div><div class="pagination">
    <?php for ($i = 1; $i <= $totalPages; $i++): ?>
        <?= $i === $page ? "<strong>$i</strong>" : "<a href=\"?folder=$selected&page=$i\">$i</a>" ?>
    <?php endfor; ?>
</div><div class="popup-overlay" id="popup">
    <img id="popup-img" src="" alt="Full View">
</div><div class="carousel-overlay" id="carousel">
    <div class="swiper">
        <div class="swiper-wrapper">
            <?php foreach ($currentFiles as $file): 
                if (preg_match('/\.(jpg|jpeg|png|gif)$/i', $file)):
                    $url = "$mediaBase/" . rawurlencode($file);
            ?>
                <div class="swiper-slide"><img src="<?= $url ?>"></div>
            <?php endif; endforeach; ?>
        </div>
        <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div>
        <div class="swiper-pagination"></div>
    </div>
</div><script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script><script>
    const popup = document.getElementById('popup');
    const popupImg = document.getElementById('popup-img');

    function openPopup(src) {
        popupImg.src = src;
        popup.style.display = 'flex';
    }

    popup.addEventListener('click', (e) => {
        if (e.target !== popupImg) {
            popup.style.display = 'none';
        }
    });

    function toggleDarkMode() {
        document.body.classList.toggle('dark');
        localStorage.setItem('darkMode', document.body.classList.contains('dark'));
    }

    function setLayout(type) {
        const container = document.getElementById('media');
        if (type === 'list') {
            container.style.display = 'flex';
            container.style.flexDirection = 'column';
            container.style.gap = '16px';
        } else {
            container.style.display = 'grid';
            container.style.gridTemplateColumns = 'repeat(auto-fill, minmax(180px, 1fr))';
            container.style.gap = '16px';
        }
    }

    function openCarousel() {
        document.getElementById('carousel').style.display = 'flex';
        new Swiper('.swiper', {
            navigation: { nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev' },
            pagination: { el: '.swiper-pagination' },
            loop: true
        });
    }

    document.getElementById('carousel').addEventListener('click', e => {
        if (!e.target.closest('.swiper')) {
            document.getElementById('carousel').style.display = 'none';
        }
    });

    // Force dark mode on initial load
    document.body.classList.add('dark');
    localStorage.setItem('darkMode', 'true');
</script></body>
</html>