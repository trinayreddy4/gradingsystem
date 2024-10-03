import React, { useState, useEffect } from 'react';
import { AppBar, Box, Button, Toolbar, Typography, TextField, Paper, Grid, MenuItem, Select, InputLabel, FormControl } from '@mui/material';
import axios from 'axios';

const Upload = () => {
  const [file, setFile] = useState(null);
  const [description, setDescription] = useState('');
  const [assignmentName, setAssignmentName] = useState('');
  const [deadline, setDeadline] = useState('');
  const [courses, setCourses] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState('');

//   useEffect(() => {
//     axios.get('/api/courses')
//       .then(response => {
//         setCourses(response.data);
//       })
//       .catch(error => {
//         console.error("Error fetching courses:", error);
//       });
//   }, []);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  const handleAssignmentNameChange = (event) => {
    setAssignmentName(event.target.value);
  };

  const handleDeadlineChange = (event) => {
    setDeadline(event.target.value);
  };

  const handleCourseChange = (event) => {
    setSelectedCourse(event.target.value);
  };

  const handleSubmit = () => {
    if (file && description && assignmentName && deadline && selectedCourse) {
      console.log('Submitting file:', file);
      console.log('Description:', description);
      console.log('Assignment Name:', assignmentName);
      console.log('Deadline:', deadline);
      console.log('Selected Course:', selectedCourse);
      // Add your file submission logic here
    } else {
      alert('Please fill in all fields');
    }
  };

  return (
    <Box sx={{ flex: 1, flexGrow: 1, height: "100vh" }}>
      <AppBar
        sx={{ height: '50px', px: 4, fontWeight: 500, display: 'flex', justifyContent: 'center' }}
        position="static"
      >
        <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
          <Typography variant="h6">Grading System</Typography>
          <Box>
            <Button variant="text">Courses</Button>
            <Button variant="text">Pending Submissions</Button>
            <Button variant="text">My Submissions</Button>
          </Box>
        </Toolbar>
      </AppBar>

      <Box sx={{ p: 3 }}>
        <Paper elevation={3} sx={{ p: 4, maxWidth: '600px', margin: 'auto' }}>
          <Typography variant="h5" gutterBottom>
            Upload Assignment
          </Typography>

          <Box sx={{ mt: 2 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  label="Assignment Name"
                  variant="outlined"
                  fullWidth
                  value={assignmentName}
                  onChange={handleAssignmentNameChange}
                />
              </Grid>

              <Grid item xs={12}>
                <FormControl fullWidth>
                  <InputLabel id="course-select-label">Select Course</InputLabel>
                  <Select
                    labelId="course-select-label"
                    value={selectedCourse}
                    label="Select Course"
                    onChange={handleCourseChange}
                  >
                    {courses.map(course => (
                      <MenuItem key={course.id} value={course.name}>
                        {course.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>

              <Grid item xs={12}>
                <TextField
                  label="Deadline Date"
                  type="date"
                  variant="outlined"
                  fullWidth
                  InputLabelProps={{ shrink: true }}
                  value={deadline}
                  onChange={handleDeadlineChange}
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  label="Assignment Description"
                  variant="outlined"
                  fullWidth
                  multiline
                  rows={3}
                  value={description}
                  onChange={handleDescriptionChange}
                />
              </Grid>

              <Grid item xs={12}>
                <Button
                  variant="contained"
                  component="label"
                >
                  Select File
                  <input
                    type="file"
                    hidden
                    onChange={handleFileChange}
                  />
                </Button>
              </Grid>

              {file && (
                <Grid item xs={12}>
                  <Typography variant="body2" gutterBottom>
                    Selected file: {file.name}
                  </Typography>
                </Grid>
              )}

              <Grid item xs={12}>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleSubmit}
                  fullWidth
                >
                  Submit
                </Button>
              </Grid>
            </Grid>
          </Box>
        </Paper>
      </Box>
    </Box>
  );
};

export default Upload;
