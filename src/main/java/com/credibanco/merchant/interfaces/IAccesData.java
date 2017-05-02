/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.credibanco.merchant.interfaces;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jonatan.velandia
 */
@FunctionalInterface
public interface IAccesData {
        public List<Map<String, Object>> queryForList(String nameQuery, Object[] args);

}
