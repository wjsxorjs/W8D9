"use client"

import React, { useState } from 'react'
import styles from "../../page.module.css";
import axios from 'axios';
import { useRouter } from 'next/navigation';

export default function page() {

  const mainStyles = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '6rem',
    minHeight: '100vh',
    justifyContent: 'flex-start'
  }

  const [member, setMember] = useState({});

  const router = useRouter();

  const API_URL = "http://localhost:8080/api/member/login"


  function signIn(){ // 비동기식 통신을 이용하여 서버에게 값 전달
    
    // axios.post(
    //   API_URL,
    //   JSON.stringify(member),
      // {
      //   withCredentials: true,
      //   headers:{'Content-Type':'application/json'},
      // },
    // ).then((res)=>{
    //   console.log(res.data);
    // });

    axios({
      url: API_URL,
      method: "post",
      params: member,
      withCredentials: true,
      headers:{'Content-Type':'application/json'},
    }).then((res)=>{
      console.log(res.data);
      if(res.status != null){
        alert("로그인 성공!");
        router.push("/")
      } else{
        alert("로그인 실패!");
      }
    })


  }

  const handleSubmit = function(e){ // 사용자가 입력한 값 검증

  }

  const handleChange = (e) => { // useState에 저장
    // 아이디 또는 비밀번호에서 입력할 때마다 호출되는 곳이다.
    const {name, value} = e.target;
    // console.log({...member, [name]:value});
    setMember({...member, [name]:value});

  }

  return (
    <div style={mainStyles}>
      <h1>로그인</h1>
      <form onSubmit={handleSubmit}>
        <table>
          <tbody>
            <tr>
              <td style={{textAlign:'right', paddingRight:'5px'}}>
                <label htmlFor='mId'>아이디: </label>
              </td>
              <td>
                <input type='text' id='mId' name='mId' onChange={handleChange} />
              </td>
            </tr>
            <tr>
              <td style={{textAlign:'right', paddingRight:'5px'}}>
                <label htmlFor='mPw'>비밀번호: </label>
              </td>
              <td>
                <input type='text' id='mPw' name='mPw' onChange={handleChange} />
              </td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colSpan={2}>
                <button type='button' onClick={signIn}>로그인</button>
              </td>
            </tr>
          </tfoot>
        </table>
      </form>
    </div>
  )
}
