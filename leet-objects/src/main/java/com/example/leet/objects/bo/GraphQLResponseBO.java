package com.example.leet.objects.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class GraphQLResponseBO {
    private DataBO data;

    public DataBO getData() {
        return data;
    }

    public void setData(DataBO data) {
        this.data = data;
    }
}
