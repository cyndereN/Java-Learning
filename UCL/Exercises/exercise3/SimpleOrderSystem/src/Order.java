import java.util.ArrayList;

public class Order
{
  private ArrayList<LineItem> lineItems;

  public Order()
  {
    lineItems = new ArrayList<LineItem>();
  }

  public int getLineItemCount()
  {
    return lineItems.size();
  }

  public void add(LineItem item)
  {
    lineItems.add(item);
  }

  public ArrayList<LineItem> getLineItems()
  {
    return new ArrayList<LineItem>(lineItems);
  }

  public int getTotal()
  {
    int total = 0;
    for (LineItem item : lineItems)
    {
      total += item.getSubTotal();
    }
    return total;
  }
}
