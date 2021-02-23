package com.example.kashless

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var mapItem: HashMap<String, List<String>>,
    private val listener: OnItemClickedListener): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    //private var mapItem = getFunctionInfo().items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_for_recyclerview,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val keyList: MutableCollection<String> = mapItem.keys
        val valueList: MutableCollection<List<String>> = mapItem.values
        holder.title.text = keyList.elementAt(position)
        holder.qty.text = valueList.elementAt(position).elementAt(0)
        holder.price.text = valueList.elementAt(position).elementAt(1)

    }

    override fun getItemCount(): Int {
       return mapItem.size
    }
    inner class ViewHolder(v:View): RecyclerView.ViewHolder(v),
    View.OnClickListener {
        var title: TextView = v.findViewById(R.id.item_title)
        var qty: TextView = v.findViewById(R.id.quantity_for_item)
        var price: TextView = v.findViewById(R.id.price_for_item)

        init{
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClicked(position)
            }
        }
    }

    interface OnItemClickedListener{
        fun onItemClicked(position: Int)
    }
}