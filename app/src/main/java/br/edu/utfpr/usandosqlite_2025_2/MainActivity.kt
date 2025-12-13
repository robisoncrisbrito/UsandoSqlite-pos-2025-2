package br.edu.utfpr.usandosqlite_2025_2

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite_2025_2.database.DatabaseHandler
import br.edu.utfpr.usandosqlite_2025_2.database.DatabaseHandler.Companion.COLUMN_NOME
import br.edu.utfpr.usandosqlite_2025_2.database.DatabaseHandler.Companion.COLUMN_TELEFONE
import br.edu.utfpr.usandosqlite_2025_2.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite_2025_2.entity.Cadastro

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler.getInstance(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        if ( intent.getIntExtra( "cod", 0 ) != 0 ) {
            binding.etCod.setText( intent.getIntExtra( "cod", 0 ).toString() )
            binding.etNome.setText( intent.getStringExtra( "nome" ) )
            binding.etTelefone.setText( intent.getStringExtra( "telefone" ) )
        } else {
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
        }
    }


    fun btSalvarOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco
        var msg = ""

        if ( binding.etCod.text.toString().isEmpty() ) {
            val cadastro = Cadastro(
                0,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            //acesso ao banco
            banco.inserir(cadastro)
            msg = "Inclusão efetuada com Sucesso."
        } else {
            val cadastro = Cadastro( binding.etCod.text.toString().toInt(),
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            banco.alterar(cadastro)
            msg = "Alteração efetuada com Sucesso."
        }

        //apresentação da devolutiva visual para o usuário
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }

    fun btExcluirOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco
        banco.excluir( binding.etCod.text.toString().toInt() )

        //apresentação da devolutiva visual para o usuário
        Toast.makeText(
            this,
            "Exclusão efetuada com Sucesso.",
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }

    fun btPesquisarOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco
        val cadastro = banco.pesquisar( binding.etCod.text.toString().toInt() )

        //apresentação da devolutiva visual para o usuário
        if ( cadastro != null ) {
            binding.etNome.setText( cadastro.nome )
            binding.etTelefone.setText( cadastro.telefone )
        } else {
            binding.etNome.setText( "" )
            binding.etTelefone.setText( "" )

            Toast.makeText(
                this,
                "Registro não encontro.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun btListarOnClick(view: View) {
        val intent = Intent( this, ListarActivity::class.java )
        startActivity( intent )
    }



//        //acesso ao banco
//        val registros = banco.listar()
//
//        //apresentação da devolutiva visual para o usuário
//        val saida = StringBuilder()
//
//        while ( registros.moveToNext() ) {
//            val nome = registros.getString(DatabaseHandler.COLUMN_NOME.toInt() )
//            val telefone = registros.getString( DatabaseHandler.COLUMN_TELEFONE.toInt() )
//
//            saida.append( " ${nome} - ${telefone} \n " )
//        }
//
//        Toast.makeText(
//            this,
//            saida.toString(),
//            Toast.LENGTH_SHORT
//        ).show()



} //fim da MainActivity