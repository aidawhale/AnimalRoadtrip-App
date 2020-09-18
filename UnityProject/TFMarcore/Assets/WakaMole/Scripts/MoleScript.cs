using Mirror;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoleScript : NetworkBehaviour
{
    public Material green;
    public Material red;

     [ClientRpc]
     void RpcPaint(int connectionId)
    {
        if (connectionId == GetInstanceID())
        {
            this.GetComponent<MeshRenderer>().material = green;
            PlayerScript.Local.CmdScore();
        }
        else
            this.GetComponent<MeshRenderer>().material = red;
    }

    bool serverClicked = false;

    [Command(ignoreAuthority = true)]
    void CmdPaint(int connectionId)
    {
        if (serverClicked)
            return;

        serverClicked = true;
        Debug.Log("CmdPaint");
        RpcPaint(connectionId);
    }

    void OnMouseDown()
    {
        CmdPaint(GetInstanceID());
    }
}
