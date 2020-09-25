using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Mirror;

public class GuacamoleAnimalController : NetworkBehaviour
{
    [SyncVar]
    public int SpawnHoleNumber = 0;

    bool serverClicked = false;

    private void Start()
    {
        Invoke("DestroyAnimal", 5);

        if (isServer) { return; }

        GameObject hole = GameObject.Find("Hole" + SpawnHoleNumber);
        if (hole != null)
        {
            transform.position = hole.transform.position;
        }
    }

    public void OnTouchDetected()
    {
        CmdPunch(GetInstanceID());
    }

    [Command(ignoreAuthority = true)]
    void CmdPunch(int connectionId)
    {
        Debug.Log("CmdPunch");
        if (serverClicked)
            return;

        serverClicked = true;

        RpcPunch(connectionId);
    }

    [ClientRpc]
    void RpcPunch(int connectionId)
    {
        Debug.Log("RpcPunch");
        if (connectionId == GetInstanceID())
        {
            // Animal punched by Local player
            Handheld.Vibrate();
            PlayerScript.Local.CmdScore();
        }

        gameObject.SetActive(false);
        CancelInvoke();
        NetworkServer.Destroy(gameObject);
    }

    public void DestroyAnimal()
    {
        NetworkServer.Destroy(gameObject);
    }
}
