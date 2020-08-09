package com.chingari.chingariweatherdemo.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chingari.chingariweatherdemo.R
import com.chingari.chingariweatherdemo.databinding.WeatherDataListItemBinding
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataModel

class WeatherDataAdapter() : RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder>()  {

    private val weatherDataItems = mutableListOf<WeatherDataModel>()

    fun updateList(updates: List<WeatherDataModel>) {
        weatherDataItems.clear()
        weatherDataItems.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<WeatherDataListItemBinding>(
            inflater,
            R.layout.weather_data_list_item, parent, false)
        return WeatherDataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherDataItems.size
    }

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        holder.bind(weatherDataItems[position])
    }

    inner class WeatherDataViewHolder(val binding: WeatherDataListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherDataModel) {
           binding.item = item
            //binding.root.setOnClickListener { onItemSelected(item) }
            binding.executePendingBindings()
        }
    }

}