package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter;

/**
 * Created by Test on 4/1/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.District;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.Street;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {//khởi tạo 1 listview có các cửa sổ có thể show ra các listview nhỏ hơn
    //các dữ liệu cần truyền vào
    private Context _context;
    private List<District> _listDataHeader;
    private List<Integer> soduong;
    // child data in format of header title, child title
    private HashMap<District, List<Street>> _listDataChild;

    public ExpandableListAdapter(Context context, List<District> listDataHeader,
                                 HashMap<District, List<Street>> listChildData, List<Integer> soduong) {
        this._context = context;
        this.soduong=soduong;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {//hàm trả về trên đường tại position nào đó
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).getNameStreet();
    }
    public Object getChildIDStret(int groupPosition, int childPosititon) {//trả về id
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).getIdStreet();
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {//tạo view cho child list
        View v;
        final String childText = (String) getChild(groupPosition, childPosition);
        final Integer childTextID=(Integer) getChildIDStret(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_thanhpho,null);//lấy giao diện từ layout đã tạo trước
        }
        else {
            v=convertView;
        }
        TextView txtListChild = (TextView) v.findViewById(R.id.lblListItem);
        TextView txtIDStret=(TextView)v.findViewById(R.id.idPhuong) ;
        txtIDStret.setText(String.valueOf(childTextID));
        txtIDStret.setVisibility(View.GONE);//ẩn textview này đi vì textview này không cần thiết phải show ra cho người dùng xem
        txtListChild.setText(childText);
        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getName();
    }
    public Object getIDDistric(int groupPosition){
        return this._listDataHeader.get(groupPosition).getId();
    }
    public Object getSoDuong(int groupPosition){
        return this.soduong.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {//tạo listview cho group listview
        View v;
        String headerTitle = (String) getGroup(groupPosition);
        Integer IDDistrict=(Integer)getIDDistric(groupPosition);
        int soduongkq=(int)getSoDuong(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.list_group_thanhpho, null);
        }
        else {
            v=convertView;
        }
        TextView txtIDDistrict=(TextView)v.findViewById(R.id.idDistricTP);
        txtIDDistrict.setVisibility(View.GONE);
        TextView lblListHeader = (TextView) v.findViewById(R.id.textviewofTP);
        Button btnSoDuong=(Button) v.findViewById(R.id.btnTongSoDuong);
        btnSoDuong.setText(String.valueOf(soduongkq).trim()+ "đường");//hiện thị số con đường có thể có trong 1 quận
        txtIDDistrict.setText(String.valueOf(IDDistrict));
        lblListHeader.setText(headerTitle);
        btnSoDuong.setFocusable(false);
        btnSoDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//sự kiện khi click vào 1 button để show ra childlisst
                if (isExpanded){
                    ((ExpandableListView) parent).collapseGroup(groupPosition);
                }
                else {
                    ((ExpandableListView) parent).expandGroup(groupPosition, true);
                }
            }
        });

        return v;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
