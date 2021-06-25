package ${PACKAGE_NAME}

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.${BINDING_CLASS}
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class ${C_NAME}Adapter(private val mListener: ${C_NAME}Interface) :
    RecyclerView.Adapter<${C_NAME}Adapter.${C_NAME}ViewHolder>() {


    private val mList = mutableListOf<${MODEL_NAME}>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ${C_NAME}ViewHolder {
        return ${C_NAME}ViewHolder(
            ${BINDING_CLASS}.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ${C_NAME}ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun set${MODEL_NAME}List(mList: List<${MODEL_NAME}>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class ${C_NAME}ViewHolder(
        val binding: ${BINDING_CLASS},
        private val mListener: ${C_NAME}Interface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                
            }

            
        }

        fun bind(item: ${MODEL_NAME}) {
            binding.apply {
                
            }
        }
    }

    interface ${C_NAME}Interface {
        
    }


}