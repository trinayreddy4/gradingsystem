import { AppBar, Box, Button, Card, TextField, Toolbar, Typography } from '@mui/material';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import React, { useState } from 'react';
import axios from 'axios';

const Login = () => {
    const [username,setUsername] = useState('');
    // console.log(username);
    const [password,setPassword] = useState('');
    // console.log(password);
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
          const response = await axios.post("http://localhost:8080/api/auth/login", {
            username,
            password
          });
          console.log(response.data);
        } catch (error) {
          console.error(error);
        }
      };
      
    return (
    <div>
      <Box sx={{ flex: 1, flexGrow: 1, height: '100vh' }}>
        <Box>
          <AppBar
            sx={{ height: '50px', px: 4, fontWeight: 500, display: 'flex', justifyContent: 'center' }}
            position="static"
          >
            <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
              <Typography variant="h6">Grading System</Typography>
              <Button variant="filled" startIcon={<LockOpenIcon />}>
                Register
              </Button>
            </Toolbar>
          </AppBar>
        </Box>
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: 'calc(100vh - 50px)', 
          }}
        >
          <Card
            variant="outlined"
            sx={{
              width: '300px',
              padding: '20px',
            }}
          >
            <Typography variant="h5" sx={{ textAlign: 'center', mb: 3 }}>
              Login
            </Typography>
            <Box
              component="form"
              sx={{
                display: 'flex',
                flexDirection: 'column',
                gap: 2, 
              }}
            >
              <TextField label="Username" variant="outlined" fullWidth onChange={(e)=>setUsername(e.target.value)} />
              <TextField label="Password" variant="outlined" type="password" fullWidth onChange={(e)=>setPassword(e.target.value)} />
              <Button
                variant="contained"
                fullWidth
                sx={{ color: 'white', fontWeight: 500 }}
                onClick={handleLogin}
              >
                Login
              </Button>
            </Box>
          </Card>
        </Box>
      </Box>
    </div>
  );
};

export default Login;
