package com.example.demo.datarepresentations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LabeledResponse<T> {
    private String label;
    private T tasks;
}
