package core.jokes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import core.jokes.R
import core.jokes.api.API
import core.jokes.api.JsonBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JokesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        API.initApi()
        val view = inflater.inflate(R.layout.fragment_jokes, container, false)
        onReload(view)
        retainInstance = true
        return view
    }

    private fun onReload(view: View) {
        val countField = view.findViewById<EditText>(R.id.count_filed)
        val btn = view.findViewById<Button>(R.id.btn_reload)
        val textField = view.findViewById<TextView>(R.id.text_field)

        btn.setOnClickListener {
            API.jokesService.getJokes(countField.text.toString()).enqueue(object : Callback<JsonBase> {
                    override fun onResponse(call: Call<JsonBase>, response: Response<JsonBase>) {
                        val body = API.getBodyFromResponse(response, JsonBase::class.java)
                        textField.text =""
                        for (item in body!!.value) {
                            textField.append(item.joke)
                        }
                    }

                    override fun onFailure(call: Call<JsonBase>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }
}