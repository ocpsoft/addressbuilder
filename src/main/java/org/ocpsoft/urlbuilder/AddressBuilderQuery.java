package org.ocpsoft.urlbuilder;


public class AddressBuilderQuery
{
   private AddressBuilder parent;

   AddressBuilderQuery(AddressBuilder parent)
   {
      this.parent = parent;
   }

   public Address build()
   {
      return parent;
   }

   public AddressBuilderAnchor anchor(CharSequence anchor)
   {
      return parent.anchor(anchor);
   }
   
   public AddressBuilderQuery query(CharSequence name, Object value)
   {
      return parent.query(name, value);
   }
}
