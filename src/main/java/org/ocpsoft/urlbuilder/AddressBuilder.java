package org.ocpsoft.urlbuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ocpsoft.urlbuilder.util.Encoder;

public class AddressBuilder
{
   private volatile Address address;
   protected volatile CharSequence protocol;
   protected volatile CharSequence host;
   protected volatile Integer port;
   protected volatile CharSequence path;
   protected Map<CharSequence, Parameter> parameters = new LinkedHashMap<CharSequence, Parameter>();
   protected Map<CharSequence, Parameter> queries = new LinkedHashMap<CharSequence, Parameter>();
   protected CharSequence anchor;

   protected AddressBuilder()
   {}

   public static AddressBuilderBase begin()
   {
      return new AddressBuilderBase(new AddressBuilder());
   }

   protected Address build()
   {
      if (address == null)
      {
         address = new AddressResult(this);
      }
      return address;
   }

   public static Address create(String url)
   {
      try {

         boolean startWithPath = url.startsWith("/");

         URL u = null;
         if (startWithPath) {
            u = new URL("http://127.0.0.1" + url);
         }
         else {
            u = new URL(url);
         }

         AddressBuilderBase builder = AddressBuilder.begin();
         if (!startWithPath) {
            builder.protocol(u.getProtocol()).host(u.getHost());
            if (u.getPort() != -1) {
               builder.port(u.getPort());
            }
         }

         return builder.path(u.getPath()).queryLiteral(u.getQuery()).anchor(u.getRef()).build();
      }
      catch (MalformedURLException e) {
         throw new RuntimeException(e);
      }
   }

   AddressBuilderProtocol protocol(CharSequence protocol)
   {
      this.protocol = protocol;
      return new AddressBuilderProtocol(this);
   }

   AddressBuilderHost host(CharSequence host)
   {
      this.host = host;
      return new AddressBuilderHost(this);
   }

   AddressBuilderPort port(int port)
   {
      this.port = port;
      return new AddressBuilderPort(this);
   }

   AddressBuilderPath path(CharSequence path)
   {
      this.path = path;
      return new AddressBuilderPath(this);
   }

   AddressBuilderQuery query(CharSequence name, Object... values)
   {
      this.queries.put(Encoder.query(name.toString()), Parameter.create(name.toString(), true, values));
      return new AddressBuilderQuery(this);
   }

   AddressBuilderQuery queryEncoded(CharSequence name, Object... values)
   {
      this.queries.put(name.toString(), Parameter.create(name.toString(), false, values));
      return new AddressBuilderQuery(this);
   }

   AddressBuilderQuery queryLiteral(String query)
   {
      if (query != null)
      {
         if (query.startsWith("?"))
            query = query.substring(1);

         Map<CharSequence, List<CharSequence>> params = new HashMap<CharSequence, List<CharSequence>>();
         query = decodeHTMLAmpersands(query);
         int index = 0;

         while ((index = query.indexOf('&')) >= 0 || !query.isEmpty())
         {
            String pair = query;
            if (index >= 0)
            {
               pair = query.substring(0, index);
               query = query.substring(index);
               if (!query.isEmpty())
                  query = query.substring(1);
            }
            else
               query = "";

            String name;
            String value;
            int pos = pair.indexOf('=');
            // for "n=", the value is "", for "n", the value is null
            if (pos == -1)
            {
               name = pair;
               value = null;
            }
            else
            {
               name = pair.substring(0, pos);
               value = pair.substring(pos + 1, pair.length());
            }

            queryEncoded(name, value);
            List<CharSequence> list = params.get(name);
            if (list == null)
            {
               list = new ArrayList<CharSequence>();
               params.put(name, list);
            }
            list.add(value);
         }

         for (Entry<CharSequence, List<CharSequence>> entry : params.entrySet()) {
            queryEncoded(entry.getKey(), entry.getValue().toArray());
         }
      }
      return new AddressBuilderQuery(this);
   }

   private String decodeHTMLAmpersands(String url)
   {
      if (url != null)
      {
         int index = 0;
         while ((index = url.indexOf("&amp;")) >= 0)
         {
            url = url.substring(0, index + 1) + url.substring(index + 5);
         }
      }
      return url;
   }

   AddressBuilderAnchor anchor(CharSequence anchor)
   {
      this.anchor = anchor;
      return new AddressBuilderAnchor(this);
   }

   void set(CharSequence name, Object... values)
   {
      this.parameters.put(name.toString(), Parameter.create(name.toString(), true, values));
   }

   void setEncoded(CharSequence name, Object... values)
   {
      this.parameters.put(name.toString(), Parameter.create(name.toString(), false, values));
   }

   @Override
   public String toString()
   {
      return build().toString();
   }
}
