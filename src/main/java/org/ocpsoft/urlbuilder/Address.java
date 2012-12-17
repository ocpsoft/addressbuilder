package org.ocpsoft.urlbuilder;

public interface Address
{
   String getAnchor();

   boolean isAnchorSet();

   String getPath();

   boolean isPathSet();

   Integer getPort();

   boolean isPortSet();

   String getHost();

   boolean isHostSet();

   String getProtocol();

   boolean isProtocolSet();

   String getQuery();

   boolean isQuerySet();
}
