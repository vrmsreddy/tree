/*
 * MIT License
 *
 * Copyright (c) 2017 JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.ms.jp.audit.controller;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.jp.audit.domain.OrderDetail;
import com.ms.jp.audit.dto.OrderDetailDTO;
import com.ms.jp.audit.service.OrderDetailService;

/**
 * @author juanca <juan.calvopina+dev@gmail.com>
 */
@RestController
@RequestMapping(value = "/order-detail")
public class OrderDetailController {
    private static final Logger logger = LoggerFactory.getLogger(OrderDetail.class);
    private static final String WELCOME_ORDER_DETAIL_ENTITY = "Welcome to Envers example: Order Detail Entity";

    @Autowired
    private OrderDetailService orderDetailService;


    @RequestMapping(value = "/find-all-details", method = RequestMethod.GET)
    public List<OrderDetail> findAllDetails() {
        logger.info("Find all order details");
        return orderDetailService.findAll();
    }

    @RequestMapping(value = "/find-by-detail", method = RequestMethod.GET)
    public OrderDetail findByDetailPk(@RequestParam(value = "productId") String productId,
                                      @RequestParam(value = "orderId") String orderId) {
        logger.info(String.format("Finding by: %s, %s", productId, orderId));
        return orderDetailService.findByDetailPk(productId, orderId);
    }

    @RequestMapping(value = "/save-detail", method = RequestMethod.POST)
    public String saveDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        Validate.notNull(orderDetailDTO, "The order detail cannot be null");
        logger.info(String.format("Saving detail: %s", orderDetailDTO.toString()));
        return orderDetailService.save(orderDetailDTO);
    }

    @RequestMapping(value = "/update-detail", method = RequestMethod.POST)
    public String updateDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        Validate.notNull(orderDetailDTO, "The order detail cannot be null");
        logger.info(String.format("Updating order detail: %s", orderDetailDTO.toString()));
        return orderDetailService.update(orderDetailDTO);
    }

    @RequestMapping(value = "/delete-detail", method = RequestMethod.DELETE)
    public String deleteDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        logger.info(String.format("Deleting order detail: %s", orderDetailDTO));
        return orderDetailService.deleteById(orderDetailDTO.getId());
    }

}
