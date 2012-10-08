package org.ocpsoft.urlbuilder;


public class AddressBuilderPath
{
   private Address parent;

   AddressBuilderPath(Address parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent.build();
   }

   public AddressBuilderQuery query(CharSequence name, Object... values)
   {
      return parent.query(name, values);
   }

   public AddressBuilderAnchor anchor(String anchor)
   {
      return parent.anchor(anchor);
   }

   public AddressBuilderPath set(CharSequence name, Object... values)
   {
      parent.set(name, values);
      return this;
   }    
   
   @Override
   public String toString()
   {
      return parent.toString();
   }
}
