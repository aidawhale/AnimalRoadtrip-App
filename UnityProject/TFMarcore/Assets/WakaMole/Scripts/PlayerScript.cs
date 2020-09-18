using Mirror;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlayerScript : NetworkBehaviour
{
    public static PlayerScript Local;

    [SyncVar]
    public int currentScore = 0;

    void Start()
    {
        DontDestroyOnLoad(this.gameObject);

        if (isLocalPlayer)
        {
            Local = this;
        }
    }

    void Update()
    {
        GetComponent<Text>().text = currentScore.ToString();
    }

    [Command]
    public void CmdScore()
    {
        currentScore++;
    }
}
