package utils;

import models.QueryOptions;

public class EndPointBuilder {

    private String endPoint;
    private static final PropertyManager env = EnvConfig.getEnvInstance();

    public EndPointBuilder() {
        this.endPoint = env.getPropertyValue("service.library");
    }

    public EndPointBuilder pathParameter(String param) {
        this.endPoint += "/" + param;
        return this;
    }

    public EndPointBuilder pathParameter(int param) {
        return this.pathParameter(String.valueOf(param));
    }

    public EndPointBuilder queryParam(String param, String value) {
        String delimiter;
        if (this.endPoint.contains("?")) delimiter = "&";
        else delimiter = "?";
        this.endPoint += delimiter + param + "=" + value;
        return this;
    }

    public EndPointBuilder queryParam(String param, int value) {
        return this.queryParam(param, String.valueOf(value));
    }

    public EndPointBuilder queryParam(String param, boolean value) {
        return this.queryParam(param, String.valueOf(value));
    }

    public EndPointBuilder applyQueryOptions(QueryOptions options) {
        if(options.orderType != null) {
            this.queryParam("orderType", options.orderType);
        }
        this
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if(options.sortBy != null) {
            this.queryParam("sortBy", options.sortBy);
        }
        return this;
    }

    public String build() {
        return this.endPoint;
    }
}
