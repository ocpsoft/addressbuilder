package org.ocpsoft.urlbuilder;


public class AddressBuilderProtocol
{
   private AddressBuilder parent;
   
   AddressBuilderProtocol(AddressBuilder parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent;
   }

   public AddressBuilderHost host(CharSequence host)
   {
      return parent.host(host);
   }

   public AddressBuilderPort port(int port)
   {
      return parent.port(port);
   }

   public AddressBuilderPath path(CharSequence path)
   {
      return parent.path(path);
   }

   public AddressBuilderQuery query(CharSequence name, Object... values)
   {
      return parent.query(name, values);
   }

   public AddressBuilderAnchor anchor(String anchor)
   {
      return parent.anchor(anchor);
   }

}
