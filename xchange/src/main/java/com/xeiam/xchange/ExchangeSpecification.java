/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
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
 */
package com.xeiam.xchange;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Specification to provide the following to {@link ExchangeFactory}:
 * </p>
 * <ul>
 * <li>Provision of required exchangeSpecificParameters for creating an {@link Exchange}</li>
 * <li>Provision of optional exchangeSpecificParameters for additional configuration</li>
 * </ul>
 */
public class ExchangeSpecification {

  private String userName;
  private String password;
  private String secretKey;
  private String apiKey;
  private String uri;
  private String version;
  private String host;
  private int port = 80;
  private final String exchangeClassName;

  private Map<String, Object> exchangeSpecificParameters = new HashMap<String, Object>();

  /**
   * Dynamic binding
   * 
   * @param exchangeClassName The exchange class name (e.g. "com.xeiam.xchange.mtgox.v1.MtGoxExchange")
   */
  public ExchangeSpecification(String exchangeClassName) {

    this.exchangeClassName = exchangeClassName;
  }

  /**
   * Static binding
   * 
   * @param exchangeClass The exchange class
   */
  public ExchangeSpecification(Class exchangeClass) {

    this.exchangeClassName = exchangeClass.getCanonicalName();
  }

  /**
   * @return The exchange class name for loading at runtime
   */
  public String getExchangeClassName() {

    return exchangeClassName;
  }

  /**
   * @param key The key into the parameter map (recommend using the provided standard static entries)
   * @return Any additional exchangeSpecificParameters that the {@link Exchange} may consume to configure services
   */
  public Object getParameter(String key) {

    return exchangeSpecificParameters.get(key);
  }

  /**
   * The host name of the server providing data (e.g. "intersango.com")
   */
  public String getHost() {

    return host;
  }

  public void setHost(String host) {

    this.host = host;
  }

  /**
   * The API key. For MtGox this would be the "Rest-Key" field
   */
  public String getApiKey() {

    return apiKey;
  }

  public void setApiKey(String apiKey) {

    this.apiKey = apiKey;
  }

  /**
   * The port number of the server providing direct socket data (e.g. "1337")
   */
  public int getPort() {

    return port;
  }

  public void setPort(int port) {

    this.port = port;
  }

  /**
   * The API secret key typically used in HMAC signing of requests. For MtGox this would be the "Rest-Sign" field
   */
  public String getSecretKey() {

    return secretKey;
  }

  public void setSecretKey(String secretKey) {

    this.secretKey = secretKey;
  }

  /**
   * The URI to reach the <b>root</b> of the exchange API<br/>
   * (e.g. use "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades")
   */
  public String getUri() {

    return uri;
  }

  public void setUri(String uri) {

    this.uri = uri;
  }

  /**
   * The numerical API version to use (e.g. "1" or "0.3" etc)
   */
  public String getVersion() {

    return version;
  }

  public void setVersion(String version) {

    this.version = version;
  }

  /**
   * Allows arbitrary exchange-specific parameters to be passed to the exchange implementation
   */
  public Map<String, Object> getExchangeSpecificParameters() {

    return exchangeSpecificParameters;
  }

  public void setExchangeSpecificParameters(Map<String, Object> exchangeSpecificParameters) {

    this.exchangeSpecificParameters = exchangeSpecificParameters;
  }

  /**
   * The password for authentication
   */
  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * The username for authentication
   */
  public String getUserName() {

    return userName;
  }

  public void setUserName(String userName) {

    this.userName = userName;
  }
}
