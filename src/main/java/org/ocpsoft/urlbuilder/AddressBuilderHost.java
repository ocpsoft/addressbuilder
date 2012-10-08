package org.ocpsoft.urlbuilder;


public class AddressBuilderHost
{
   private AddressBuilder parent;

   AddressBuilderHost(AddressBuilder parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent;
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
