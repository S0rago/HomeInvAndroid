package ru.sorago.homeinvandroid.ui.itemcreator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import ru.sorago.homeinvandroid.R;
import ru.sorago.homeinvandroid.api.ApiClient;
import ru.sorago.homeinvandroid.api.SessionManager;
import ru.sorago.homeinvandroid.core.IAppConstants;
import ru.sorago.homeinvandroid.core.LayoutParamsUtils;
import ru.sorago.homeinvandroid.core.ViewTag;
import ru.sorago.homeinvandroid.data.model.ItemProp;
import ru.sorago.homeinvandroid.data.request.ItemRequest;
import ru.sorago.homeinvandroid.data.response.base.RecordResponse;
import ru.sorago.homeinvandroid.data.response.base.Response;
import ru.sorago.homeinvandroid.data.response.type.ItemData;

public class ItemCreatorFragment extends Fragment {

    private View root;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private int fieldCount;
    private int rowCount;
    private Button saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fieldCount = 0;
        rowCount = 0;
        root = inflater.inflate(R.layout.fragment_item_add, container, false);
        scrollView = root.findViewById(R.id.item_props_sv);
        linearLayout = root.findViewById(R.id.item_props_ll);
        saveBtn = root.findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(v -> saveItem());
        addEditTextRow();
        return root;
    }

    private void addEditTextRow() {
        LinearLayout ll2ViewBinder = new LinearLayout(getContext());
        ll2ViewBinder.setOrientation(LinearLayout.HORIZONTAL);
        ll2ViewBinder.setWeightSum(IAppConstants.WEIGHT_SUM);

        EditText editText1 = new EditText(getActivity());
        editText1.setId(View.generateViewId());
        editText1.setTag(new ViewTag("propedit", fieldCount));
        fieldCount++;
        editText1.setLayoutParams(LayoutParamsUtils.getLLParamWeightWCWC(IAppConstants.WEIGHT_0POINT3));
        editText1.setHint(R.string.prop_name_hint);

        EditText editText2 = new EditText(getActivity());
        editText2.setId(View.generateViewId());
        editText2.setLayoutParams(LayoutParamsUtils.getLLParamWeightMPWC(IAppConstants.WEIGHT_0POINT7));
        editText2.setTag(new ViewTag("propedit", fieldCount));
        fieldCount++;
        editText2.setHint(R.string.prop_val_hint);


        if (rowCount == 0) {
            editText1.setText(R.string.prop_name);
            editText1.setEnabled(false);
            editText2.setEnabled(true);
        } else if (rowCount == 1) {
            editText1.setText(R.string.prop_time);
            editText1.setEnabled(false);
            editText2.setEnabled(true);
        } else {
            editText2.setEnabled(false);
        }

        editText1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                editText2.setEnabled(s.length() != 0 && editText1.getText().length() != 0);
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0 && editText2.getText().length() != 0) {
                    View next = root.findViewWithTag(((ViewTag) editText1.getTag()).getNext(2));
                    if (next == null) {
                        addEditTextRow();
                        Log.d("New row", String.valueOf(rowCount));
                    }
                } else {
                    View old1 = linearLayout.findViewWithTag(((ViewTag) editText1.getTag()).getNext(4));
                    if (old1 == null) {
                        linearLayout.removeView(linearLayout.findViewWithTag(new ViewTag("row", rowCount - 1)));
                        linearLayout.removeView(linearLayout.findViewWithTag(new ViewTag("propedit", (rowCount - 1) * 2)));
                        linearLayout.removeView(linearLayout.findViewWithTag(new ViewTag("propedit", (rowCount - 1) * 2 + 1)));
                        Log.d("Delete row", String.valueOf(rowCount + 1));
                        rowCount--;
                        fieldCount -= 2;
                    }
                }
            }
        });

        try {
            ll2ViewBinder.addView(editText1);
            ll2ViewBinder.addView(editText2);
            ll2ViewBinder.setTag(new ViewTag("row", rowCount));
            rowCount++;
            linearLayout.addView(ll2ViewBinder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveItem() {
        ItemRequest request = new ItemRequest();
        Set<ItemProp> props = new HashSet<>();
        final int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount - 1; i += 1) {
            LinearLayout rowLl = (LinearLayout) linearLayout.getChildAt(i);
            EditText et1 = (EditText) rowLl.getChildAt(0);
            EditText et2 = (EditText) rowLl.getChildAt(1);
            String s1 = et1.getText().toString();
            String s2 = et2.getText().toString();
            if (s1.toLowerCase().equals("name")) {
                request.setName(s2);
            } else if (s1.toLowerCase().equals("type")) {
                request.setTypeStr(s2.toLowerCase());
            } else {
                props.add(new ItemProp(s1, s2));
            }
        }
        request.setProps(props);
        ApiClient.getApiService().addItem(SessionManager.fetchAuthToken(getActivity().getApplicationContext()), request).enqueue(new Callback<RecordResponse<ItemData>>() {
            @Override
            public void onResponse(Call<RecordResponse<ItemData>> call, retrofit2.Response<RecordResponse<ItemData>> response) {
                if (response.body() != null && response.body().getMessage() != null) {
                    Toast.makeText(root.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordResponse<ItemData>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(root.getContext(), "Nope, server ded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
