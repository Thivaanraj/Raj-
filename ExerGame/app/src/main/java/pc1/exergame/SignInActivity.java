package pc1.exergame;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etPass;
    private EditText etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViewById(R.id.signupbtn).setOnClickListener(this);
        findViewById(R.id.loginbnt).setOnClickListener(this);
        findViewById(R.id.logoutbtn).setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.login);
        etPass = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    System.out.println(user.getUid() + " signed in");
                } else {
                    System.out.println("currently signed out");
                }
            }
        };

        updateStatus();
    }

    private boolean checkFormFields() {
        String email, password;

        email = etEmail.getText().toString();
        password = etPass.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email Required");
            return false;
        }
        if (password.isEmpty()){
            etPass.setError("Password Required");
            return false;
        }

        return true;
    }

    private void signUserIn() {
        if (!checkFormFields()) return;

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                }
                updateStatus();
            }
        });


    }

    private void signOut(){
        mAuth.signOut();
        updateStatus();
    }

    private void createUserAccount(){
        if (!checkFormFields()) return;

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "User was created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateStatus(){
        TextView tvStat = (TextView) findViewById(R.id.tvSignInStatus);
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            tvStat.setText("Signed in: " + user.getEmail());
        } else {
            tvStat.setText("Signed Out");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbnt:
                signUserIn();
                break;

            case R.id.signupbtn:
                createUserAccount();
                break;

            case R.id.logoutbtn:
                signOut();
                break;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
