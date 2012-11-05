package org.ocpsoft.urlbuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ocpsoft.urlbuilder.util.CaptureType;
import org.ocpsoft.urlbuilder.util.CapturingGroup;
import org.ocpsoft.urlbuilder.util.ParseTools;

public class Address
{
   private volatile CharSequence protocol;
   private volatile CharSequence host;
   private volatile Integer port;
   private volatile CharSequence path;
   private Map<CharSequence, Parameter> parameters = new LinkedHashMap<CharSequence, Parameter>();
   private Map<CharSequence, Parameter> queries = new LinkedHashMap<CharSequence, Parameter>();
   private CharSequence anchor;

   private Address()
   {}

   public static AddressBuilderBase begin()
   {
      return new AddressBuilderBase(new Address());
   }

   public Address build()
   {
      return this;
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
      this.queries.put(name.toString(), Parameter.create(name.toString(), true, values));
      return new AddressBuilderQuery(this);
   }

   AddressBuilderQuery queryEncoded(CharSequence name, Object... values)
   {
      this.queries.put(name.toString(), Parameter.create(name.toString(), false, values));
      return new AddressBuilderQuery(this);
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
      StringBuilder result = new StringBuilder();

      if (isSet(protocol))
         result.append(parameterize(protocol)).append("://");

      if (isSet(host))
         result.append(parameterize(host));

      if (isSet(port))
      {
         result.append(":").append(port);
      }

      if (isSet(path))
      {
         if (path.charAt(0) != '/')
            result.append('/');
         result.append(parameterize(path));
      }

      if (!queries.isEmpty())
      {
         result.append('?');
         boolean first = true;
         for (CharSequence name : queries.keySet()) {
            Parameter parameter = queries.get(name);
            for (int i = 0; i < parameter.getValueCount(); i++) {

               if (!first)
                  result.append('&');
               else
                  first = false;

               result.append(name).append('=').append(parameter.getValueAsQueryParam(i));
            }
         }
      }

      if (isSet(anchor))
         result.append('#').append(anchor);

      return result.toString();
   }

   private CharSequence parameterize(CharSequence sequence)
   {
      StringBuilder result = new StringBuilder();
      int cursor = 0;
      int lastEnd = 0;
      while (cursor < sequence.length())
      {
         switch (sequence.charAt(cursor))
         {
         case '{':
            result.append(sequence.subSequence(lastEnd, cursor));

            int startPos = cursor;
            CapturingGroup group = ParseTools.balancedCapture(sequence, startPos, sequence.length() - 1,
                     CaptureType.BRACE);
            cursor = group.getEnd();
            lastEnd = group.getEnd() + 1;

            String name = group.getCaptured().toString();

            Parameter parameter = parameters.get(name);
            if (parameter == null || !parameter.hasValues())
               throw new IllegalStateException("No parameter [" + name + "] was set in the pattern [" + sequence
                        + "]. Call address.set(\"" + name + "\", value); or remove the parameter from the pattern.");

            result.append(parameter.getValueAsPathParam(0));

            break;

         default:
            break;
         }

         cursor++;
      }

      if (cursor >= lastEnd)
         result.append(sequence.subSequence(lastEnd, cursor));
      return result;
   }

   private boolean isSet(Integer port)
   {
      return port != null;
   }

   private boolean isSet(CharSequence value)
   {
      return value != null && value.length() > 0;
   }
}
