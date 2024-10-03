import { Box, Typography } from '@mui/material'
import React from 'react'

const SubmissionDueCard = () => {
  return (
    <Box
        sx={{
            width:"1000px",
            height:"60px",
            marginTop:"20px",
            backgroundColor:"white",
            padding:"5px",
            borderRadius:"10px",
            marginLeft:"20px",
            marginBottom:"20px"
        }}
    >
        <Box
            sx={{
                display:"flex",
                gap:"5px",
                justifyContent:"space-between"
            }}
        >
            <Box
                sx={{
                    display:"flex",
                    alignItems:"flex-end"
                }}
            >
               <Typography
                    sx = {{
                        color:"black",
                        fontWeight:"500",
                        paddingLeft:"10px"
                    }}
               >
                JFSD LAB 1 SUBMISSION
               </Typography>
            </Box>
            <Box
                sx={{
                    paddingRight:"10px",
                    paddingTop:"10px"
                }}
            >
                <Box
                    sx={{
                        display:"flex"
                    }}
                >
                    <Box>
                        12-11-2004
                    </Box>
                </Box>
                </Box>
            </Box>
            <Box>
                <Typography
                    sx = {{
                        fontWeight:"400",
                        fontSize:"small",
                        paddingLeft:"10px"
                    }}
                >
                    22SDP1213 JFSD 
                </Typography>
            </Box>
    </Box>
  )
}

export default SubmissionDueCard