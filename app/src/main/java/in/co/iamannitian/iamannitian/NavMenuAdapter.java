package in.co.iamannitian.iamannitian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NavMenuAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> menuItems;
    private Map<String,List<String>> subMenuItems;
    private List<Integer> menuIcons;

    NavMenuAdapter(Context context, List<String> menuItems, Map<String, List<String>> subMenuItems, List<Integer> menuIcons) {
        this.context = context;
        this.menuItems = menuItems;
        this.subMenuItems = subMenuItems;
        this.menuIcons = menuIcons;
    }

    @Override
    public int getGroupCount() {
        return menuItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(subMenuItems.get(menuItems.get(groupPosition)) == null) {
            return 0;
        }
        return Objects.requireNonNull(subMenuItems.get(menuItems.get(groupPosition))).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return menuItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(subMenuItems.get(menuItems.get(groupPosition))).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String item = (String) getGroup(groupPosition);
        Integer itemImage = menuIcons.get(groupPosition);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nav_list_parent,null);
        }

        ImageView imgParent = convertView.findViewById(R.id.imgParent);
        TextView txtParent = convertView.findViewById(R.id.txtParent);
        imgParent.setImageResource(itemImage);
        txtParent.setText(item);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String subitem = (String) getChild(groupPosition,childPosition);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nav_list_child,null);
        }

        TextView txtChild = convertView.findViewById(R.id.txtChild);
        txtChild.setText(subitem);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
