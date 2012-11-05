package org.ocpsoft.urlbuilder;


public class AddressBuilderHost
{
   private Address parent;

   AddressBuilderHost(Address parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent.build();
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

   public AddressBuilderQuery queryEncoded(CharSequence name, Object... values)
   {
      return parent.queryEncoded(name, values);
   }

   public AddressBuilderAnchor anchor(String anchor)
   {
      return parent.anchor(anchor);
   }  
   
   @Override
   public String toString()
   {
      return parent.toString();
   }

}
