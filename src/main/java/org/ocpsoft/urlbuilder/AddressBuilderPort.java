package org.ocpsoft.urlbuilder;


public class AddressBuilderPort
{
   private AddressBuilder parent;

   AddressBuilderPort(AddressBuilder parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent;
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
