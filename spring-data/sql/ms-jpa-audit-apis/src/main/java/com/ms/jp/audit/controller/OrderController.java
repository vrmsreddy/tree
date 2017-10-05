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

import com.ms.jp.audit.domain.Order;
import com.ms.jp.audit.dto.OrderDTO;
import com.ms.jp.audit.service.OrderService;


/**
 * @author juanca <juan.calvopina+dev@gmail.com>
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private static final String WELCOME_ORDER_ENTITY = "Welcome to Envers example: Order Entity";

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/find-all-orders",method = RequestMethod.GET)
    public List<Order> findAllOrders() {
        logger.info("Find all orders");
        return orderService.findAll();
    }

    @RequestMapping(value = "/find-by-order",method = RequestMethod.GET)
    public List<Order> findByTest(@RequestParam(value = "text") String text) {
        logger.info(String.format("Finding by: %s", text));
        return orderService.findByText(text, text, text, text, text);
    }

    @RequestMapping(value = "/save-order", method = RequestMethod.POST)
    public String saveOrder(@RequestBody OrderDTO orderDTO) {
        Validate.notNull(orderDTO, "The order cannot be null");
        logger.info(String.format("Saving order: %s", orderDTO.toString()));
        return orderService.save(orderDTO);
    }

    @RequestMapping(value = "/update-order", method = RequestMethod.POST)
    public String updateOrder(@RequestBody OrderDTO orderDTO) {
        Validate.notNull(orderDTO, "The order cannot be null");
        logger.info(String.format("Updating order: %s", orderDTO.toString()));
        return orderService.update(orderDTO);
    }

    @RequestMapping(value = "/delete-order", method = RequestMethod.DELETE)
    public String deleteOrder(@RequestParam(value = "id") int id) {
        logger.info(String.format("Deleting order: %s", id));
        return orderService.deleteById(id);
    }

}
