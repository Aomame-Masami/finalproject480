package com.bn.finalp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestBooksRequest {

    private int book_index;
    private int top_n;
}
