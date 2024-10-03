import { AppBar, Box, Button, Toolbar, Typography } from '@mui/material'
import React from 'react'
import SubmissionDueCard from '../../components/SubmissionDueCard/SubmissionDueCard'

const Home = () => {
  return (
    <Box sx={{ flex: 1, flexGrow: 1, height: '100vh' }}>
        <Box>
          <AppBar
            sx={{ height: '50px', px: 4, fontWeight: 500, display: 'flex', justifyContent: 'center' }}
            position="static"
          >
            <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
              <Typography variant="h6">Grading System</Typography>
              <Box>
                <Button variant="filled" >
                    Courses
                </Button>
                <Button variant="filled" >
                    Pending Submissions
                </Button>
                <Button variant="filled" >
                    My Submissions
                </Button>
              </Box>
            </Toolbar>
          </AppBar>
        </Box>
        <Box
            sx={{
                display:"flex",
                justifyContent:"center",
                marginTop:"70px",
                flexGrow:1
             }}
        >
            <Box>
            <Box 
                sx={{
                    width:"1100px",
                    backgroundColor:"black",
                    minHeight:"20px",
                    borderRadius:"10px",
                    padding:"10px",
                    marginBottom:"10px",
                    color:"black",
                    fontWeight:"600",
                    bgcolor:"#F5F5F6"
                }}
            >
                Upcoming Submissions
            </Box>
            <Box
                sx={{
                    width:"1100px",
                    backgroundColor:"black",
                    minHeight:"200px",
                    borderRadius:"10px",
                    padding:"10px",
                    bgcolor:"#F5F5F6",
                    marginBottom:"50px"
                }}
            >
                <SubmissionDueCard/>
                <SubmissionDueCard/>
                <SubmissionDueCard/>
                <SubmissionDueCard/>
                <SubmissionDueCard/>
                <SubmissionDueCard/>
            </Box>
            
            </Box>
            <Box>

            </Box>
        </Box>
    </Box>
  )
}

export default Home