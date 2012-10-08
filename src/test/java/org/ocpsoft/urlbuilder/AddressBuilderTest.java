package org.ocpsoft.urlbuilder;

import junit.framework.Assert;

import org.junit.Test;

public class AddressBuilderTest
{

   @Test
   public void testBuildEverything()
   {
      Assert.assertEquals("http://example.com:8080/search/table?q=query string#foo",

               AddressBuilder.begin()
                        .protocol("http")
                        .host("example.com")
                        .port(8080)
                        .path("/{s}/{t}")
                        .set("s", "search")
                        .set("t", "table")
                        .query("q", "query string")
                        .anchor("foo")
                        .toString());
   }

   @Test
   public void testBuildQuery()
   {
      Assert.assertEquals("?q=200",
               AddressBuilder.begin()
                        .query("q", 200).toString());
   }

   @Test
   public void testBuildQueryMultipleNames()
   {
      Assert.assertEquals("?q=query&e=string",
               AddressBuilder.begin()
                        .query("q", "query").query("e", "string").toString());
   }

   @Test
   public void testBuildQueryMultipleValues()
   {
      Assert.assertEquals("?q=10&q=20",
               AddressBuilder.begin()
                        .query("q", 10, 20).toString());
   }

   @Test
   public void testBuildPathSimple()
   {
      Assert.assertEquals("/store/23",
               AddressBuilder.begin()
                        .path("/store/23").toString());
   }

   @Test
   public void testBuildPathWithOneParameter()
   {
      Assert.assertEquals("/store/23",
               AddressBuilder.begin()
                        .path("/store/{item}").set("item", 23).toString());
   }

   @Test
   public void testBuildPathWithParameters()
   {
      Assert.assertEquals("/store/23/buy",
               AddressBuilder.begin()
                        .path("/store/{item}/{action}").set("item", 23).set("action", "buy").toString());
   }

   @Test
   public void testBuildHostAndPath()
   {
      Assert.assertEquals("ocpsoft.org/store/23/buy",
               AddressBuilder.begin()
                        .host("ocpsoft.org")
                        .path("/store/{item}/{action}").set("item", 23).set("action", "buy").toString());
   }

   @Test
   public void testProtocolAndPort()
   {
      Assert.assertEquals("file://:80",
               AddressBuilder.begin()
                        .protocol("file")
                        .port(80).toString());
   }

}
