package com.chingari.chingariweatherdemo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import kotlinx.android.synthetic.main.weather_data_list_item.view.*

class WeatherDataAdapter(var weatherDataItems: List<WeatherModel>) : RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder>()  {

    fun updateList(updates: List<WeatherModel>) {
        weatherDataItems = updates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        //TODO : DATA BINDING MISSING HERE
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_data_list_item, parent, false)
        return WeatherDataViewHolder(v)
    }

    override fun getItemCount(): Int {
        return weatherDataItems.size
    }

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        holder.bindItems(weatherDataItems[position])
    }

    inner class WeatherDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item: WeatherModel) {
            //TODO : Ugly approach , get rid from it.
            itemView.textViewTemp.text = item.temperature
            itemView.textViewHumidity.text = item.humidity
            itemView.textViewWindspeed.text = item.windspeed
        }

    }

}