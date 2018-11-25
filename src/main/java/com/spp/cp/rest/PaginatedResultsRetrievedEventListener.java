package com.spp.cp.rest;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;


@Component
class PaginatedResultsRetrievedEventListener implements ApplicationListener<PaginatedResultsRetrievedEvent> {

    public static String createLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }

    @Override
    public void onApplicationEvent(final PaginatedResultsRetrievedEvent ev) {
        if (ev == null) {
            throw new IllegalArgumentException("PaginatedResultsRetrievedEvent is null");
        }
        // debug
        System.out.println("### received paginated event for processing: " + ev.toString());

        addLinkHeaderOnPagedResourceRetrieval(ev.getUriBuilder(), ev.getResponse(), ev.getClazz(), ev.getPage(),
                ev.getTotalPages(), ev.getPageSize());
    }

    void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                               final Class clazz, final int page, final int totalPages,
                                               final int pageSize) {
        final String resourceName = clazz.getSimpleName().toLowerCase();
        uriBuilder.path("/" + resourceName);

        final StringBuilder linkHeader = new StringBuilder();
        if (hasNextPage(page, totalPages)) {
            final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
            linkHeader.append(createLinkHeader(uriForNextPage, "next"));
        }
        if (hasPreviousPage(page)) {
            final String uriForPrevPage = constructPrevPageUri(uriBuilder, page, pageSize);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForPrevPage, "prev"));
        }
        if (hasFirstPage(page)) {
            final String uriForFirstPage = constructFirstPageUri(uriBuilder, pageSize);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForFirstPage, "first"));
        }
        if (hasLastPage(page, totalPages)) {
            final String uriForLastPage = constructLastPageUri(uriBuilder, totalPages, pageSize);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForLastPage, "last"));
        }
        response.addHeader("Link", linkHeader.toString());
    }

    String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam("page", page + 1).replaceQueryParam("size", size)
                .build().encode().toUriString();
    }

    String constructPrevPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        // -1 because we use 0 based indexing for the pages
        return uriBuilder.replaceQueryParam("page", page - 1).replaceQueryParam("size", size)
                .build().encode().toUriString();
    }

    String constructFirstPageUri(final UriComponentsBuilder uriBuilder, final int size) {
        return uriBuilder.replaceQueryParam("page", 0).replaceQueryParam("size", size)
                .build().encode().toUriString();
    }

    String constructLastPageUri(final UriComponentsBuilder uriBuilder, final int totalPages, final int size) {
        return uriBuilder.replaceQueryParam("page", totalPages - 1).replaceQueryParam("size", size)
                .build().encode().toUriString();
    }

    boolean hasNextPage(final int page, final int totalPages) {
        return page < totalPages - 1;
    }

    boolean hasPreviousPage(final int page) {
        return page > 0;
    }

    boolean hasFirstPage(final int page) {
        return hasPreviousPage(page);
    }

    boolean hasLastPage(final int page, final int totalPages) {
        return totalPages > 1 && hasNextPage(page, totalPages);
    }

    void appendCommaIfNecessary(final StringBuilder linkHeader) {
        if (linkHeader.length() > 0) {
            linkHeader.append(", ");
        }
    }

}