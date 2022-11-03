import axios from 'axios';
import { LoginInputsType, SuccessLoginType } from './types';

const requestLogin = async ({ email, password }: LoginInputsType) => {
  try {
    const { data }: { data: SuccessLoginType } = await axios({
      method: 'POST',
      url: '/api/v1/members/login',
      data: {
        email,
        password,
      },
    });
    return true;
  } catch (e) {
    console.log(e);
    return false;
  }
};

export default requestLogin;