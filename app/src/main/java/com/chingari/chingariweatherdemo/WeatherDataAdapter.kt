package com.chingari.chingariweatherdemo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.chingari.chingariweatherdemo.databinding.WeatherDataListItemBinding
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel

class WeatherDataAdapter() : RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder>()  {

    private val weatherDataItems = mutableListOf<WeatherModel>()

    fun updateList(updates: List<WeatherModel>) {
        weatherDataItems.clear()
        weatherDataItems.addAll(updates)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<WeatherDataListItemBinding>(
            inflater, R.layout.weather_data_list_item, parent, false)
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

        fun bind(item: WeatherModel) {
            binding.textViewTemp.text = item.temperature
            binding.textViewHumidity.text = item.humidity
            binding.textViewWindspeed.text = item.windspeed
            binding.textViewDate.text =item.dateCreated
            //binding.root.setOnClickListener { onItemSelected(item) }
            binding.executePendingBindings()
        }

    }

}