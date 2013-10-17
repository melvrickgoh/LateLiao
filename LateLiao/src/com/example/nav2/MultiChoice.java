package com.example.nav2;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MultiChoice extends AlertDialog {
	private ListView listView;

	  private Map<Object, Boolean> optionsWithSelection;
	  private Collection options;
	  private Collection selection;

	  public MultiChoice(Context context, Collection options,
	      Collection<Object> selection) {
	    super(context);
	    this.options = options;
	    this.selection = selection;
	  }

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    //super.onCreate(savedInstanceState);
	    Context ctx = getContext();
	    LinearLayout layout = new LinearLayout(ctx);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    
	    listView = new ListView(ctx);
	    final MultiChoiceFriendsListAdapter adapter;
	    adapter = new MultiChoiceFriendsListAdapter (ctx, options, selection);
	    listView.setAdapter(adapter);

	    if (options.size() > 10) {
	      EditText search = new EditText(ctx);
	      search.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
	      search.addTextChangedListener(new TextWatcher() {
	        @Override
	        public void onTextChanged(CharSequence s, int start,
	            int before, int count) {
	          adapter.setFilter(s.toString());
	          adapter.notifyDataSetChanged();
	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start,
	            int count, int after) {
	        }

	        @Override
	        public void afterTextChanged(Editable s) {
	        }
	      });

	      //layout.addView(search);
	    }

	    layout.addView(listView);
	    setContentView(layout);
	  }

	  public Map<Object, Boolean> getOptionsMap() {
	    return optionsWithSelection;
	  }

	  public Set<Object> getSelection() {
	    Set<Object> result = new LinkedHashSet<Object>();
	    for (Entry<Object, Boolean> e : optionsWithSelection.entrySet())
	      if (Boolean.TRUE.equals(e.getValue()))
	        result.add(e.getKey());
	    return result;
	  }
}
