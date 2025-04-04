package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * ApiErrorDetail
 */
@Data
public class ApiErrorDetail {

  private @Nullable String invalidValue;

  private @Nullable String message;

  private @Nullable String path;

  private @Nullable String type;

  public ApiErrorDetail invalidValue(String invalidValue) {
    this.invalidValue = invalidValue;
    return this;
  }

  /**
   * Get invalidValue
   * @return invalidValue
   */
  
  @Schema(name = "invalidValue", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("invalidValue")
  public String getInvalidValue() {
    return invalidValue;
  }

  public void setInvalidValue(String invalidValue) {
    this.invalidValue = invalidValue;
  }

  public ApiErrorDetail message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   */
  
  @Schema(name = "message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ApiErrorDetail path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
   */
  
  @Schema(name = "path", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ApiErrorDetail type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiErrorDetail apiErrorDetail = (ApiErrorDetail) o;
    return Objects.equals(this.invalidValue, apiErrorDetail.invalidValue) &&
        Objects.equals(this.message, apiErrorDetail.message) &&
        Objects.equals(this.path, apiErrorDetail.path) &&
        Objects.equals(this.type, apiErrorDetail.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(invalidValue, message, path, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiErrorDetail {\n");
    sb.append("    invalidValue: ").append(toIndentedString(invalidValue)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

