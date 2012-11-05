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

   @Override
   public String toString()
   {
      return parent.toString();
   }
}
