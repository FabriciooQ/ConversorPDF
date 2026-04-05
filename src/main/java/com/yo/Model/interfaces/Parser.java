package com.yo.Model.interfaces;

import java.util.Map;

public interface Parser {
    Map<String, String> parseTableHeader(String[] lines);
    Map<Integer, String[]> parseContentTable(String[] lineas, boolean firstPage);
    Map<String, String> parseHeader(String[] lines);
    
}
