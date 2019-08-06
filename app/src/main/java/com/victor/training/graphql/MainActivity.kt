package com.victor.training.graphql

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.victor.training.graphql.data.models.DomainModel
import com.victor.training.graphql.presenter.GitHubPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GitHubPresenter.GitHubView {

    private lateinit var mGitHubPresenter: GitHubPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mGitHubPresenter = GitHubPresenter()
        mGitHubPresenter.setView(this)
        mGitHubPresenter.getSomeInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        mGitHubPresenter.destroy()
    }


    // ----------------------------------------------------------------------------
    // ------------------------------ PRESENTER VIEW ------------------------------
    override fun onResponse(responseModel: DomainModel) {
        println("victor - onResponse :: $responseModel")
        val receivedText = "${responseModel.firstField}\n${responseModel.secondField}"
        txtRetrievedContent.text = receivedText
    }

    override fun onError(error: Throwable) {
        println("victor - onError :: ${error.message}")
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }
}
