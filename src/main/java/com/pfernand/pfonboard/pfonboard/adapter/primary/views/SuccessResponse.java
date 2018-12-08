package com.pfernand.pfonboard.pfonboard.adapter.primary.views;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonTypeName(value = "successResponse")
@JsonTypeInfo(
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        use = JsonTypeInfo.Id.NAME
)
public class SuccessResponse {
    private final long currentTime;
    private final JsonNode inputParameters;
}
