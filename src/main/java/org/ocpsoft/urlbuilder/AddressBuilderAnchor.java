package org.ocpsoft.urlbuilder;


public class AddressBuilderAnchor implements Address
{
   private AddressBuilder parent;

   AddressBuilderAnchor(AddressBuilder parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent;
   }

   public AddressBuilderAnchor set(CharSequence name, Object... values)
   {
      parent.set(name, values);
      return this;
   }  
}
