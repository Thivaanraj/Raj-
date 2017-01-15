package pc1.exergame.menu.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import pc1.exergame.R;

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

        autoLogin();

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

    private void signUserIn(final View view) {
        if (!checkFormFields()) return;

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        email = email.replace(" ", "");

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    toastMessage(SignInActivity.this, "Signed In");
                    goToMain(view);
                } else {
                    toastMessage(SignInActivity.this, "Sign In failed");
                }
                updateStatus();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    toastMessage(SignInActivity.this, "Invalid Password");
                } else if (e instanceof FirebaseAuthInvalidUserException){
                    toastMessage(SignInActivity.this, "Invalid email");
                } else {
                    toastMessage(SignInActivity.this, e.getLocalizedMessage());
                }
            }
        });
    }


    private void toastMessage(SignInActivity obj, String message){
        Toast.makeText(obj, message, Toast.LENGTH_SHORT).show();
    }

    private void signOut(){
        mAuth.signOut();
        updateStatus();
    }

    private void createUserAccount(){
        if (!checkFormFields()) return;

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        email = email.replace(" ", "");

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    toastMessage(SignInActivity.this, "User was created");
                } else {
                    toastMessage(SignInActivity.this, "Account creation failed");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException){
                    toastMessage(SignInActivity.this, "Email in use");
                } else {
                    toastMessage(SignInActivity.this, e.getLocalizedMessage());
                }
            }
        });
    }

    private void autoLogin(){
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
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
                signUserIn(v);
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

    // <<<<<<<<<< INTENTS >>>>>>>>>>
    public void goToMain(View view){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }


}
