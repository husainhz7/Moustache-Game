/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
// $Id: Sphere.java 4163 2009-03-25 01:14:55Z matt.yellen $
package com.jme3.scene.shape;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import com.jme3.util.Tem                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 along the radial.
     * @param radius
     *            The radius of the sphere.
     * @param useEvenSlices
     *            Slice sphere evenly along the Z axis
     * @param interior
     *            Not yet documented
     */
    public Sphere(int zSamples, int radialSamples, float radius, boolean useEvenSlices, boolean interior) {
        updateGeometry(zSamples, radialSamples, radius, useEvenSlices, interior);
    }

    public int getRadialSamples() {
        return radialSamples;
    }

    public float getRadius() {
        return radius;
    }

    /**
     * @return Returns the textureMode.
     */
    public TextureMode getTextureMode() {
        return textureMode;
    }

    public int getZSamples() {
        return zSamples;
    }

    /**
     * builds the vertices based on the radius, radial and zSamples.
     */
    private void setGeometryData() {
        // allocate vertices
        vertCount = (zSamples - 2) * (radialSamples + 1) + 2;

        FloatBuffer posBuf = BufferUtils.createVector3Buffer(vertCount);

        // allocate normals if requested
        FloatBuffer normBuf = BufferUtils.createVector3Buffer(vertCount);

        // allocate texture coordinates
        FloatBuffer texBuf = BufferUtils.createVector2Buffer(vertCount);

        setBuffer(Type.Position, 3, posBuf);
        setBuffer(Type.Normal, 3, normBuf);
        setBuffer(Type.TexCoord, 2, texBuf);

        // generate geometry
        float fInvRS = 1.0f / radialSamples;
        float fZFactor = 2.0f / (zSamples - 1);

        // Generate points on the unit circle to be used in computing the mesh
        // points on a sphere slice.
        float[] afSin = new float[(radialSamples + 1)];
        float[] afCos = new float[(radialSamples + 1)];
        for (int iR = 0; iR < radialSamples; iR++) {
            float fAngle = FastMath.TWO_PI * fInvRS * iR;
            afCos[iR] = FastMath.cos(fAngle);
            afSin[iR] = FastMath.sin(fAngle);
        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      .z);
                }

                if (textureMode == TextureMode.Original) {
                    texBuf.put(fRadialFraction).put(
                            0.5f * (fZFraction + 1.0f));
                } else if (textureMode == TextureMode.Projected) {
                    texBuf.put(fRadialFraction).put(
                            FastMath.INV_PI
                            * (FastMath.HALF_PI + FastMath.asin(fZFraction)));
                } else if (textureMode == TextureMode.Polar) {
                    float r = (FastMath.HALF_PI - FastMath.abs(fAFraction)) / FastMath.PI;
                    float u = r * afCos[iR] + 0.5f;
                    float v = r * afSin[iR] + 0.5f;
                    texBuf.put(u).put(v);
                }

                i++;
            }

            BufferUtils.copyInternalVector3(posBuf, iSave, i);
            BufferUtils.copyInternalVector3(normBuf, iSave, i);

            if (textureMode == TextureMode.Original) {
                texBuf.put(1.0f).put(
                        0.5f * (fZFraction + 1.0f));
            } else if (textureMode == TextureMode.Projected) {
                texBuf.put(1.0f).put(
                        FastMath.INV_PI
                        * (FastMath.HALF_PI + FastMath.asin(fZFraction)));
            } else if (textureMode == TextureMode.Polar) {
                float r = (FastMath.HALF_PI - FastMath.abs(fAFraction)) / FastMath.PI;
                texBuf.put(r + 0.5f).put(0.5f);
            }

            i++;
        }

        vars.release();

        // south pole
        posBuf.position(i * 3);
        posBuf.put(0f).put(0f).put(-radius);

        normBuf.position(i * 3);
        if (!interior) {
            normBuf.put(0).put(0).put(-1); // allow for inner
        } // texture orientation
        // later.
        else {
            normBuf.put(0).put(0).put(1);
        }

        texBuf.position(i * 2);

        if (textureMode == TextureMode.Polar) {
            texBuf.put(0.5f).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               idxBuf.put((short) (vertCount - 2));
                idxBuf.put((short) (i + 1));
            } else { // inside view
                idxBuf.put((short) i);
                idxBuf.put((short) (i + 1));
                idxBuf.put((short) (vertCount - 2));
            }
        }

        // north pole triangles
        int iOffset = (zSamples - 3) * (radialSamples + 1);
        for (int i = 0; i < radialSamples; i++, index += 3) {
            if (!interior) {
                idxBuf.put((short) (i + iOffset));
                idxBuf.put((short) (i + 1 + iOffset));
                idxBuf.put((short) (vertCount - 1));
            } else { // inside view
                idxBuf.put((short) (i + iOffset));
                idxBuf.put((short) (vertCount - 1));
                idxBuf.put((short) (i + 1 + iOffset));
            }
        }
    }

    /**
     * @param textureMode
     *            The textureMode to set.
     */
    public void setTextureMode(TextureMode textureMode) {
        this.textureMode = textureMode;
        setGeometryData();
    }

    /**
     * Changes the information of the sphere into the given values.
     * 
     * @param zSamples the number of zSamples of the sphere.
     * @param radialSamples the number of radial samples of the sphere.
     * @param radius the radius of the sphere.
     */
    public void updateGeometry(int zSamples, int radialSamples, float radius) {
        if (zSamples < 3) {
            throw new IllegalArgumentException("zSamples cannot be smaller than 3");
        }
        updateGeometry(zSamples, radialSamples, radius, false, false);
    }

    public void updateGeometry(int zSamples, int radialSamples, float radius, boolean useEvenSlices, boolean interior) {
        if (zSamples < 3) {
            throw new IllegalArgumentException("zSamples cannot be smaller than 3");
        }
        this.zSamples = zSamples;
        this.radialSamples = radialSamples;
        this.radius = radius;
        th                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    