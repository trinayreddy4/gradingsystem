import { AppBar, Box, Button, Card, MenuItem, Select, TextField, Toolbar, Typography } from '@mui/material';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import React, { useState } from 'react';
import axios from 'axios';

const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('STUDENT'); 

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
          const response = await axios.post("http://localhost:8080/api/auth/register", {
            username,
            password,
            role 
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
              <Button variant="contained" startIcon={<LockOpenIcon />} onClick={() => window.location.href='/login'}>
                Login
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
              Register
            </Typography>
            <Box
              component="form"
              sx={{
                display: 'flex',
                flexDirection: 'column',
                gap: 2, 
              }}
            >
              <TextField label="Username" variant="outlined" fullWidth onChange={(e) => setUsername(e.target.value)} />
              <TextField label="Password" variant="outlined" type="password" fullWidth onChange={(e) => setPassword(e.target.value)} />
              <Select
                value={role}
                onChange={(e) => setRole(e.target.value)}
                fullWidth
              >
                <MenuItem value="STUDENT">Student</MenuItem>
                <MenuItem value="TEACHER">Teacher</MenuItem>
              </Select>
              <Button
                variant="contained"
                fullWidth
                sx={{ color: 'white', fontWeight: 500 }}
                onClick={handleRegister}
              >
                Register
              </Button>
            </Box>
          </Card>
        </Box>
      </Box>
    </div>
  );
};

export default Register;
