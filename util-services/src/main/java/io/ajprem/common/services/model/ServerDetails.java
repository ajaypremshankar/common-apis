/**
 *
 */
package io.ajprem.common.services.model;

/**
 * Class to store and transfer different server details
 *
 */
public class ServerDetails {

	private String protocol;
	private String host;
	private String port;
	private String contextPath;

	private String url;

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 * @deprecated (when, why, refactoring advice...)
	 *
	 */
	@Deprecated
	public ServerDetails(final String protocol, final String host, final String port) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;

		StringBuilder localURL = new StringBuilder(protocol).append("://").append(host).append(":").append(port);
		this.url = localURL.toString();
	}

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 */
	public ServerDetails(final String protocol, final String host, final String port, final String contextPath) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.contextPath = contextPath;
		StringBuilder localURL = new StringBuilder(protocol).append("://").append(host).append(":").append(port);

		if (contextPath != null) {
			localURL.append("/").append(contextPath);
		}

		this.url = localURL.toString();
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	public String getUrl() {
		return url;
	}

	public String getContextPath() {
		return contextPath;
	}

}
