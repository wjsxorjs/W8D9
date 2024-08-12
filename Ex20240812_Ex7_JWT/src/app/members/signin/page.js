"use client"

import React from 'react'
import styles from "../../page.module.css";

export default function page() {

  const mainStyles = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '6rem',
    minHeight: '100vh',
    justifyContent: 'flex-start'
  }


  function signIn(){ // 비동기식 통신을 이용하여 서버에게 값 전달

  }

  const handleSubmit = function(e){ // 사용자가 입력한 값 검증

  }

  const handleChange = (e) => { // useState에 저장

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
