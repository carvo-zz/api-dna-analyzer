package br.com.mercadolivre.mutantidentifier.integratedtest;

public final class UriResolver {

    private UriResolver() { }

    /**
     * Creates the target URI to test
     *
     * @param port configs port
     * @param targetResource should <b>start with slash</b>
     * @return localhost:<code>port</code>/<code>targetResource</code>
     */
    public static String localhost(final int port, final String targetResource) {
        return "http://localhost:" + port + targetResource;
    }
}