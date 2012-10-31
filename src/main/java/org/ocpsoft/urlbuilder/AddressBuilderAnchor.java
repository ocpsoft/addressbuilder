package org.ocpsoft.urlbuilder;


public class AddressBuilderAnchor
{
   private Address parent;

   AddressBuilderAnchor(Address parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent.build();
   }

   public AddressBuilderAnchor set(CharSequence name, Object... values)
   {
      parent.set(name, values);
      return this;
   }  

   public AddressBuilderAnchor set(CharSequence name, boolean encode, Object... values)
   {
      parent.set(name, encode, values);
      return this;
   }
   
   @Override
   public String toString()
   {
      return parent.toString();
   }
}
